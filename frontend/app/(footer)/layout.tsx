import type { Metadata } from "next";
import "@/app/globals.css";
import Footer from "../components/footer";

export const metadata: Metadata = {
  title: "PetPal",
  description: " PetPal Programming Exercise",
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <>
      {children}
      <Footer />
    </>
  );
}
