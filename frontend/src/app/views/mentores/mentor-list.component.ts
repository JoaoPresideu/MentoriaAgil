import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { PerfilMentorService } from '../../services/perfil-mentor.service';
import { PerfilMentor } from '../../models/PerfilMentor';
import { SolicitacaoMentoriaModalComponent } from './mentoria/solicitacao-mentoria-modal.component';

@Component({
  selector: 'app-mentor-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './mentor-list.component.html',
  styleUrls: ['./mentor-list.component.css']
})
export class MentorListComponent implements OnInit {
  private mentorService = inject(PerfilMentorService);
  private dialog = inject(MatDialog);
  private snackBar = inject(MatSnackBar);

  mentores: PerfilMentor[] = [];

  filtros = {
    areaPrincipal: '',
    tipoMentoria: '',
    disponibilidade: '',
    ordenacao: ''
  };

  ngOnInit(): void {
    this.buscar();
  }

  buscar(): void {
    this.mentorService.buscarMentores(this.filtros).subscribe(data => {
      let lista = data.filter(m => m.ativo);
      if (this.filtros.ordenacao === 'nome') {
        lista = lista.sort((a, b) => a.name.localeCompare(b.name));
      }
      this.mentores = lista;
    });
  }

  onFiltroChange(): void {
    this.buscar();
  }

  solicitarMentoria(mentor: PerfilMentor): void {
    const dialogRef = this.dialog.open(SolicitacaoMentoriaModalComponent, {
      width: '500px',
      data: { mentor }
    });
  }
}