import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { AuthorNameComponent } from './components/author-name/author-name.component';
import { TopicNameComponent } from './components/topic-name/topic-name.component';



@NgModule({
  declarations: [
    AuthorNameComponent,
    TopicNameComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    AuthorNameComponent,
    TopicNameComponent
  ]
})
export class SharedModule { }
