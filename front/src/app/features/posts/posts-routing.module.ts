import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreateComponent } from './components/create/create.component';
import { ListComponent } from './components/list/list.component';

const routes: Routes = [
  {
    title: 'MDD - All posts from subscribed topics', path: '', component: ListComponent
  },
  {
    title: 'MDD - Create a new post', path: 'create', component: CreateComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PostsRoutingModule { }
