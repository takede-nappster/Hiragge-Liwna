import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'jeux',
        loadChildren: () => import('./jeux/jeux.module').then(m => m.TestJeuxModule),
      },
      {
        path: 'competition',
        loadChildren: () => import('./competition/competition.module').then(m => m.TestCompetitionModule),
      },
      {
        path: 'match',
        loadChildren: () => import('./match/match.module').then(m => m.TestMatchModule),
      },
      {
        path: 'utilisateur',
        loadChildren: () => import('./utilisateur/utilisateur.module').then(m => m.TestUtilisateurModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class TestEntityModule {}
