import "@/app/globals.css";
import NavBar from "../components/nav-bar";

export default function NoFooterLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <div className="flex flex-col w-screen h-screen overflow-x-hidden overflow-y-hidden">
      <NavBar />
      <div className="grow">
        {children}
      </div>
    </div>
  );
}
