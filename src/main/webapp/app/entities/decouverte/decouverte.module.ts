import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestSharedModule } from 'app/shared/shared.module';
import { DecouverteComponent } from './decouverte.component';
import { DecouverteDetailComponent } from './decouverte-detail.component';
import { decouverteRoute } from './decouverte.route';

@NgModule({
  imports: [TestSharedModule, RouterModule.forChild(decouverteRoute)],
  declarations: [DecouverteComponent, DecouverteDetailComponent],
})
export class TestDecouverteModule {}
