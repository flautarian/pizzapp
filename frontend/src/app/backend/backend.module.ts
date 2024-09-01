import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BackendComponent } from './backend.component';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [
    BackendComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild([{ path: '', component: BackendComponent }])
  ]
})
export class BackendModule { 

    
}