import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestSharedModule } from 'app/shared/shared.module';
import { JeuxComponent } from './jeux.component';
import { JeuxDetailComponent } from './jeux-detail.component';
import { JeuxUpdateComponent } from './jeux-update.component';
import { JeuxDeleteDialogComponent } from './jeux-delete-dialog.component';
import { jeuxRoute } from './jeux.route';

@NgModule({
  imports: [TestSharedModule, RouterModule.forChild(jeuxRoute)],
  declarations: [JeuxComponent, JeuxDetailComponent, JeuxUpdateComponent, JeuxDeleteDialogComponent],
  entryComponents: [JeuxDeleteDialogComponent],
})
export class TestJeuxModule {}
