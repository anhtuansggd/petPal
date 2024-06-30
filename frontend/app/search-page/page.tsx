import NavBar from "../components/nav-bar";
import FAQ from "./faq";
import SearchBar from "./search-bar";
import Test from "./test";

export default function Page() {
  return (
    <div className="flex min-h-screen flex-col">
      <NavBar />
      <SearchBar />
      <FAQ />
      {/* <Test /> */}
    </div>
  );
}
