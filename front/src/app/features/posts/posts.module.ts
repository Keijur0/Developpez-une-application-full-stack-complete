import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { HttpClientModule } from '@angular/common/http';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { ListComponent } from './components/list/list.component';
import { PostsRoutingModule } from './posts-routing.module';
import { CreateComponent } from './components/create/create.component';


@NgModule({
  declarations: [
    ListComponent,
    CreateComponent
  ],
  imports: [
    CommonModule,
    PostsRoutingModule,
    HttpClientModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule
  ]
})
export class PostsModule { }
