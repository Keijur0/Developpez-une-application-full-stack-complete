import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './guards/auth.guard';
import { UnauthGuard } from './guards/unauth.guard';
import { HomeComponent } from './pages/home/home.component';
import { TopicComponent } from './pages/topic/topic.component';

// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
const routes: Routes = [
  {
    title: 'MDD - Welcome',
    path: '',
    canActivate: [UnauthGuard],
    component: HomeComponent
  },
  {
    title: 'MDD - All topics',
    path: 'topic',
    canActivate: [AuthGuard],
    component: TopicComponent
  },
  {
    path: 'auth',
    canActivate: [UnauthGuard],
    loadChildren: () => import('./features/auth/auth.module').then(m => m.AuthModule)
  },
  {
    path: 'posts',
    canActivate: [AuthGuard],
    loadChildren: () => import('./features/posts/posts.module').then(m => m.PostsModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
