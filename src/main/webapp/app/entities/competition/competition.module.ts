import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestSharedModule } from 'app/shared/shared.module';
import { CompetitionComponent } from './competition.component';
import { CompetitionDetailComponent } from './competition-detail.component';
import { CompetitionUpdateComponent } from './competition-update.component';
import { CompetitionDeleteDialogComponent } from './competition-delete-dialog.component';
import { competitionRoute } from './competition.route';

@NgModule({
  imports: [TestSharedModule, RouterModule.forChild(competitionRoute)],
  declarations: [CompetitionComponent, CompetitionDetailComponent, CompetitionUpdateComponent, CompetitionDeleteDialogComponent],
  entryComponents: [CompetitionDeleteDialogComponent],
})
export class TestCompetitionModule {}
