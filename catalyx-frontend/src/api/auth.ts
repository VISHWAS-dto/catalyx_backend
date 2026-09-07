import { apiClient } from "./client";

export interface LoginRequest {
  email: string;
  password: string;
}

export interface SignupRequest {
  email: string;
  password: string;
  name: string;
}

export interface AuthResponse {
  token: string;
  tokenType: string;
  userId: number;
  email: string;
  name: string;
}

export const authApi = {
  login: (payload: LoginRequest) => apiClient.post<AuthResponse, LoginRequest>("/login", payload),
  signup: (payload: SignupRequest) => apiClient.post<AuthResponse, SignupRequest>("/signup", payload),
};
