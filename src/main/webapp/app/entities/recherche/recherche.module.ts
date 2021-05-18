import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestSharedModule } from 'app/shared/shared.module';
import { RechercheComponent } from './recherche.component';
import { RechercheDetailComponent } from './recherche-detail.component';
import { RechercheUpdateComponent } from './recherche-update.component';
import { RechercheDeleteDialogComponent } from './recherche-delete-dialog.component';
import { rechercheRoute } from './recherche.route';

@NgModule({
  imports: [TestSharedModule, RouterModule.forChild(rechercheRoute)],
  declarations: [RechercheComponent, RechercheDetailComponent, RechercheUpdateComponent, RechercheDeleteDialogComponent],
  entryComponents: [RechercheDeleteDialogComponent],
})
export class TestRechercheModule {}
