export interface Post {
    id: number;
    topicId: number;
    title: string;
    authorId: number;
    content: string;
    createdAt: Date
}