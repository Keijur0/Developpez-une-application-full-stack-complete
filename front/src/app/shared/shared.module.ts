import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { AuthorNameComponent } from './components/author-name/author-name.component';



@NgModule({
  declarations: [
    AuthorNameComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    AuthorNameComponent
  ]
})
export class SharedModule { }
