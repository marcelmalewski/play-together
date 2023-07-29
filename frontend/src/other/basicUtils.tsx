import { UNKNOWN_ERROR } from "./constants";

export function handleErrorMessage(error: any): string {
  return error.data.message.split(",")[0] || UNKNOWN_ERROR;
}
