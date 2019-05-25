enum AdType {
  LINEAR = 0, OVERLAY, IN_BANNER
}


export class Ad {
  id: number;
  name: string;
  duration: number;
  active: boolean;
  type: AdType;
  fileName: string;
  category: number;
  importance: number;

}
