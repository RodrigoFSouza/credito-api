export interface ApiError {
    timestamp?: string;
    status: number;
    error: string;
    message: string;
    path?: string;
}
  
export interface ApiErrorResponse {
    message: string;
    statusCode: number;
    isClientError: boolean;
    isServerError: boolean;
}