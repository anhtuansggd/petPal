import NavBar from "../components/nav-bar";
import UserProfile from "./user-profile";

export default function Page() {
  return (
    <main className="flex min-h-screen flex-col">
      <NavBar />
      <UserProfile />
    </main>
  );
}
