export interface ErrorResponse {
  readonly status: number;
  readonly message: string;
  readonly data: string | null;
}
