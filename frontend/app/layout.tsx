import NavBar from "./components/nav-bar";
import type { Metadata } from "next";
import { Poppins } from "next/font/google";

import "./globals.css";
import Footer from "./components/footer";

const poppins = Poppins({ weight: ["400", "600"], subsets: ["latin"] });

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
    <html lang="en">

      <head>
        <link
          rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"
        />
      </head>
      <body className={poppins.className}>
        <NavBar />
        {children}
        <Footer />

    
      </body>
    </html>
  );
}
