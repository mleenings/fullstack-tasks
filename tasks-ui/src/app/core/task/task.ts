export class Task {
    id: string;
    text: string;
    done: boolean;


    constructor(values: Object = {done: false}) {
      Object.assign(this, values);
    }
}
