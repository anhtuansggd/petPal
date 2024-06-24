import NavBar from "../components/nav-bar";
import FAQ from "./faq";
import SearchBar from "./search-bar";
import Test from "./test";

export default function Page() {
  return (
    <main className="flex min-h-screen flex-col">
      <SearchBar />
      <FAQ />
      {/* <Test /> */}
    </main>
  );
}
