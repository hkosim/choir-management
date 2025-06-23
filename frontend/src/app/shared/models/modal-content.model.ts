import { ModalAction } from "./modal-action.model";

export interface ModalContent {
  title: string;
  content: string;
  actions: ModalAction[];
}
