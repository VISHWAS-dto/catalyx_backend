export interface ApiErrorBody {
  timestamp?: string;
  status: number;
  error: string;
  message: string;
  path?: string;
}

export class ApiError extends Error {
  status: number;
  body?: ApiErrorBody;

  constructor(message: string, status: number, body?: ApiErrorBody) {
    super(message);
    this.name = "ApiError";
    this.status = status;
    this.body = body;
  }
}

const BASE_URL = import.meta.env.VITE_API_BASE_URL ?? "http://localhost:8080";

async function request<TResponse>(
  path: string,
  options: RequestInit = {}
): Promise<TResponse> {
  const response = await fetch(`${BASE_URL}${path}`, {
    ...options,
    headers: {
      "Content-Type": "application/json",
      ...options.headers,
    },
  });

  const isJson = response.headers.get("content-type")?.includes("application/json");
  const data = isJson ? await response.json() : undefined;

  if (!response.ok) {
    const body = data as ApiErrorBody | undefined;
    throw new ApiError(body?.message ?? "Something went wrong. Please try again.", response.status, body);
  }

  return data as TResponse;
}

export const apiClient = {
  post: <TResponse, TBody = unknown>(path: string, body: TBody) =>
    request<TResponse>(path, {
      method: "POST",
      body: JSON.stringify(body),
    }),
  get: <TResponse>(path: string, token?: string) =>
    request<TResponse>(path, {
      method: "GET",
      headers: token ? { Authorization: `Bearer ${token}` } : undefined,
    }),
};
