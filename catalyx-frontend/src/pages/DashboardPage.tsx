import { useAuth } from "../auth/AuthContext";

export function DashboardPage() {
  const { user, logout } = useAuth();

  return (
    <div style={{ minHeight: "100vh", display: "flex", alignItems: "center", justifyContent: "center", flexDirection: "column", gap: 12 }}>
      <h1>Welcome{user ? `, ${user.name}` : ""} 👋</h1>
      <p>You're logged in as {user?.email}.</p>
      <button onClick={logout} style={{ padding: "8px 16px", cursor: "pointer" }}>
        Log out
      </button>
    </div>
  );
}
