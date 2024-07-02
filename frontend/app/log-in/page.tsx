import Footer from "../components/footer";
import NavBar from "../components/nav-bar";
import LogIn from "./log-in";

export default function Page() {
  return (
    <div className="flex min-h-screen flex-col">
      <NavBar />
      <LogIn />
    </div>
  );
}
