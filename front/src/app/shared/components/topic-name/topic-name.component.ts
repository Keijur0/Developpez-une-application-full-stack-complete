import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { Topic } from 'src/app/interfaces/topic.interface';
import { TopicService } from 'src/app/services/topic.service';

@Component({
  selector: 'app-topic-name',
  templateUrl: './topic-name.component.html',
  styleUrls: ['./topic-name.component.scss']
})
export class TopicNameComponent implements OnChanges {

  @Input()
  public topicId!: number;

  public topicName: string | null = null;

  constructor(private topicService: TopicService) {}

  ngOnChanges(changes: SimpleChanges): void {
    if(changes['topicId'].currentValue !== changes['topicId'].previousValue) {
      this.topicService.getTopic(changes['topicId'].currentValue).subscribe((topic: Topic) => {
        this.topicName = topic.name;
      });
    }
  }

}
