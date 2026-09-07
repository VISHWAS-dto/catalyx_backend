import { createContext, useContext, useMemo, useState, type ReactNode } from "react";
import type { AuthResponse } from "../api/auth";

export interface AuthUser {
  userId: number;
  email: string;
  name: string;
}

interface AuthContextValue {
  user: AuthUser | null;
  token: string | null;
  isAuthenticated: boolean;
  login: (auth: AuthResponse) => void;
  logout: () => void;
}

const STORAGE_KEY = "catalyx.auth";

const AuthContext = createContext<AuthContextValue | undefined>(undefined);

function readStoredAuth(): { token: string; user: AuthUser } | null {
  try {
    const raw = localStorage.getItem(STORAGE_KEY);
    if (!raw) return null;
    return JSON.parse(raw);
  } catch {
    return null;
  }
}

export function AuthProvider({ children }: { children: ReactNode }) {
  const stored = readStoredAuth();
  const [token, setToken] = useState<string | null>(stored?.token ?? null);
  const [user, setUser] = useState<AuthUser | null>(stored?.user ?? null);

  const login = (auth: AuthResponse) => {
    const nextUser: AuthUser = { userId: auth.userId, email: auth.email, name: auth.name };
    setToken(auth.token);
    setUser(nextUser);
    localStorage.setItem(STORAGE_KEY, JSON.stringify({ token: auth.token, user: nextUser }));
  };

  const logout = () => {
    setToken(null);
    setUser(null);
    localStorage.removeItem(STORAGE_KEY);
  };

  const value = useMemo<AuthContextValue>(
    () => ({ user, token, isAuthenticated: Boolean(token), login, logout }),
    [user, token]
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth(): AuthContextValue {
  const ctx = useContext(AuthContext);
  if (!ctx) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return ctx;
}
