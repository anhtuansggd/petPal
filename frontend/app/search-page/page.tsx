import FAQ from "./faq";
import SearchBar from "./search-bar";
import Test from "./test";

export default function Page() {
  return (
    <div className="flex min-h-screen flex-col">
      <SearchBar />
      <FAQ />
      {/* <Test /> */}
    </div>
  );
}
