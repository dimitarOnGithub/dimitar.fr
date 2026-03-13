export class ArticleRelation {

  currentArticleId: number;
  private _previousArticleId!: number | undefined;
  private _nextArticleId!: number | undefined;

  constructor(currentArticleId: number | string) {
    this.currentArticleId = Number(currentArticleId);
  }

  previousArticleId(): number | undefined {
    return this._previousArticleId;
  }

  setPreviousIfEmpty(value: number) {
    if(!this._previousArticleId){
      this._previousArticleId = value;
    }
  }

  nextArticleId(): number | undefined {
    return this._nextArticleId;
  }

  setNextIfEmpty(value: number) {
    if(!this._nextArticleId){
      this._nextArticleId = value;
    }
  }

}
