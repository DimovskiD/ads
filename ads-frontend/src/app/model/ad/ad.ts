export enum AdType {
  LINEAR = 0, OVERLAY
}

export enum Category {
  AUTOMOTIVE = 0, BEVERAGES, FOOD, HEALTH
}

export class Ad {
  id: number;
  name: string;
  duration: number;
  active: boolean;
  type: AdType;
  fileName: string;
  category: Category;
  importance: number;

}
