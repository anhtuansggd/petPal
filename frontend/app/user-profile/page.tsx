import NavBar from "../components/nav-bar";
import Footer from "../components/footer";
import UserProfile from "./user-profile";

export default function Page() {
  return (
    <main className="flex h-screen flex-col justify-between">
      <NavBar />
      <UserProfile />
      <Footer />
    </main>
  );
}
