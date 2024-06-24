import HeroSection from "./hero-section";
import Map from "./map";
import Services from "./services";
import Test from "./test";
import Testimonials from "./testimonials";

export default function Page() {
  return (
    <main className="flex min-h-screen flex-col">
      <HeroSection />
      <Services />
      <Testimonials />
      <Map />
      {/* <Test /> */}
    </main>
  );
}
