"use client";
import React from "react";
import {
  Accordion,
  AccordionHeader,
  AccordionBody,
} from "@material-tailwind/react";

function Icon({ id, open }: { id: any; open: any }) {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      fill="none"
      viewBox="0 0 24 24"
      strokeWidth={2}
      stroke="currentColor"
      className={`${
        id === open ? "rotate-180" : ""
      } h-5 w-5 transition-transform`}
    >
      <path
        strokeLinecap="round"
        strokeLinejoin="round"
        d="M19.5 8.25l-7.5 7.5-7.5-7.5"
      />
    </svg>
  );
}

export default function FAQ() {
  const [open, setOpen] = React.useState(0);

  const handleOpen = (value: any) => setOpen(open === value ? 0 : value);

  return (
    <div className="my-12">
      <div className=" text-center text-6xl mb-10 text-primary-light-green font-bold">
        <h1>FAQs</h1>
      </div>

      <div className="w-6/12 mx-auto mt-4">
        <Accordion open={open === 1} icon={<Icon id={1} open={open} />}>
          <AccordionHeader
            className="text-primary-light-green"
            onClick={() => handleOpen(1)}
          >
            What is PetPal?
          </AccordionHeader>
          <AccordionBody>
            Lorem ipsum, dolor sit amet consectetur adipisicing elit. Quidem,
            est quo. Quos provident ex eos quia ipsam quisquam similique natus,
            quo quis excepturi, facere consectetur dolore exercitationem quod
            animi reprehenderit!
          </AccordionBody>
        </Accordion>
        <Accordion open={open === 2} icon={<Icon id={2} open={open} />}>
          <AccordionHeader
            className="text-primary-light-green"
            onClick={() => handleOpen(2)}
          >
            How to use PetPal?
          </AccordionHeader>
          <AccordionBody>
            Lorem ipsum dolor, sit amet consectetur adipisicing elit. Quibusdam,
            nobis. Qui, fugit. Hic voluptate fugiat non itaque dolorum quos
            mollitia eveniet at illum esse aliquid alias, necessitatibus impedit
            nostrum blanditiis!
          </AccordionBody>
        </Accordion>
        <Accordion open={open === 3} icon={<Icon id={3} open={open} />}>
          <AccordionHeader
            className="text-primary-light-green"
            onClick={() => handleOpen(3)}
          >
            What can I do with PetPal?
          </AccordionHeader>
          <AccordionBody>
            Lorem ipsum dolor sit amet consectetur adipisicing elit.
            Reprehenderit distinctio voluptas quisquam corrupti aperiam
            laboriosam. Quae, deserunt consequatur! Excepturi aliquid ab eius,
            veniam totam vel quae nihil repellendus. Repellendus, vel.
          </AccordionBody>
        </Accordion>
      </div>
    </div>
  );
}
