import type { Metadata } from "next";
import "@/app/globals.css";

export const metadata: Metadata = {
  title: "PetPal",
  description: " PetPal Programming Exercise",
};

export default function NoFooterLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <>
      {children}
    </>
  );
}
