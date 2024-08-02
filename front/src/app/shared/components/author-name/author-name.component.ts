import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { User } from 'src/app/interfaces/user.interface';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-author-name',
  templateUrl: './author-name.component.html',
  styleUrls: ['./author-name.component.scss']
})
export class AuthorNameComponent implements OnChanges {

  @Input()
  public authorId!: number;

  public authorName: string | null = null;

  constructor(private userService: UserService) {}


  ngOnChanges(changes: SimpleChanges): void {
    if(changes['authorId'].currentValue !== changes['authorId'].previousValue) {
      this.userService.getUser(changes['authorId'].currentValue).subscribe((user: User) => {
        this.authorName = user.username
      });
    }
  }



}
