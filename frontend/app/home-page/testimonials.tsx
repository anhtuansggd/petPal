"use client";
import React from "react";
import Image from "next/image";

import { Avatar, Rating } from "@material-tailwind/react";

// Array of offer data objects

export default function Testimonials() {
  return (
    <div className="w-8/12 mt-24 mx-auto text-center flex items-center justify-center">
      <div className="w-full mx-auto">
        <div className="text-6xl mb-10 text-primary-light-green font-bold">
          <h1>Over 1000+ people trust us</h1>
        </div>

        <div className=" md:flex items-start">
          <div className="px-3 md:w-1/3">
            <div className="w-full mx-auto rounded-lg bg-white border border-gray-200 p-5 text-gray-800 font-light mb-6">
              <div className="w-full flex mb-4 justify-center items-center">
                <div className="overflow-hidden rounded-full w-12 h-12 bg-gray-50 border border-gray-200">
                  <Avatar src="https://i.pravatar.cc/100?img=1" />
                </div>
                <div className="pl-3">
                  <h6 className="font-bold text-sm uppercase text-gray-600">
                    Kenzie Edgar.
                  </h6>
                  <Rating value={5} />
                </div>
              </div>
              <div className="w-full">
                <p className="text-sm leading-tight">
                  <span className="text-lg leading-none italic font-bold text-gray-400 mr-1">
                    "
                  </span>
                  Lorem ipsum dolor sit amet consectetur adipisicing elit. Quos
                  sunt ratione dolor exercitationem minima quas itaque saepe
                  quasi architecto vel! Accusantium, vero sint recusandae cum
                  tempora nemo commodi soluta deleniti.
                  <span className="text-lg leading-none italic font-bold text-gray-400 ml-1">
                    "
                  </span>
                </p>
              </div>
            </div>
            <div className="w-full mx-auto rounded-lg bg-white border border-gray-200 p-5 text-gray-800 font-light mb-6">
              <div className="w-full flex mb-4 justify-center items-center">
                <div className="overflow-hidden rounded-full w-12 h-12 bg-gray-50 border border-gray-200">
                  <Avatar src="https://i.pravatar.cc/100?img=8" />
                </div>
                <div className="pl-3">
                  <h6 className="font-bold text-sm uppercase text-gray-600">
                    Stevie Tifft.
                  </h6>
                  <Rating value={5} />
                </div>
              </div>
              <div className="w-full">
                <p className="text-sm leading-tight">
                  <span className="text-lg leading-none italic font-bold text-gray-400 mr-1">
                    "
                  </span>
                  Lorem ipsum, dolor sit amet, consectetur adipisicing elit.
                  Dolore quod necessitatibus, labore sapiente, est, dignissimos
                  ullam error ipsam sint quam tempora vel.
                  <span className="text-lg leading-none italic font-bold text-gray-400 ml-1">
                    "
                  </span>
                </p>
              </div>
            </div>
          </div>
          <div className="px-3 md:w-1/3">
            <div className="w-full mx-auto rounded-lg bg-white border border-gray-200 p-5 text-gray-800 font-light mb-6">
              <div className="w-full flex mb-4 justify-center items-center">
                <div className="overflow-hidden rounded-full w-12 h-12 bg-gray-50 border border-gray-200">
                  <Avatar src="https://i.pravatar.cc/100?img=3" />
                </div>
                <div className="pl-3">
                  <h6 className="font-bold text-sm uppercase text-gray-600">
                    Tommie Ewart.
                  </h6>
                  <Rating value={5} />
                </div>
              </div>
              <div className="w-full">
                <p className="text-sm leading-tight">
                  <span className="text-lg leading-none italic font-bold text-gray-400 mr-1">
                    "
                  </span>
                  Lorem ipsum dolor sit amet, consectetur adipisicing elit.
                  Vitae, obcaecati ullam excepturi dicta error deleniti sequi.
                  <span className="text-lg leading-none italic font-bold text-gray-400 ml-1">
                    "
                  </span>
                </p>
              </div>
            </div>
            <div className="w-full mx-auto rounded-lg bg-white border border-gray-200 p-5 text-gray-800 font-light mb-6">
              <div className="w-full flex mb-4 justify-center items-center">
                <div className="overflow-hidden rounded-full w-12 h-12 bg-gray-50 border border-gray-200">
                  <Avatar src="https://i.pravatar.cc/100?img=4" />
                </div>
                <div className="pl-3">
                  <h6 className="font-bold text-sm uppercase text-gray-600">
                    Charlie Howse.
                  </h6>
                  <Rating value={5} />
                </div>
              </div>
              <div className="w-full">
                <p className="text-sm leading-tight">
                  <span className="text-lg leading-none italic font-bold text-gray-400 mr-1">
                    "
                  </span>
                  Lorem ipsum dolor sit amet consectetur adipisicing elit.
                  Architecto inventore voluptatum nostrum atque, corrupti, vitae
                  esse id accusamus dignissimos neque reprehenderit natus, hic
                  sequi itaque dicta nisi voluptatem! Culpa, iusto.
                  <span className="text-lg leading-none italic font-bold text-gray-400 ml-1">
                    "
                  </span>
                </p>
              </div>
            </div>
          </div>
          <div className="px-3 md:w-1/3">
            <div className="w-full mx-auto rounded-lg bg-white border border-gray-200 p-5 text-gray-800 font-light mb-6">
              <div className="w-full flex mb-4 justify-center items-center">
                <div className="overflow-hidden rounded-full w-12 h-12 bg-gray-50 border border-gray-200">
                  <Avatar src="https://i.pravatar.cc/100?img=5" />
                </div>
                <div className="pl-3">
                  <h6 className="font-bold text-sm uppercase text-gray-600">
                    Nevada Herbertson.
                  </h6>
                  <Rating value={5} />
                </div>
              </div>
              <div className="w-full">
                <p className="text-sm leading-tight">
                  <span className="text-lg leading-none italic font-bold text-gray-400 mr-1">
                    "
                  </span>
                  Lorem ipsum dolor sit amet consectetur adipisicing elit.
                  Nobis, voluptatem porro obcaecati dicta, quibusdam sunt ipsum,
                  laboriosam nostrum facere exercitationem pariatur deserunt
                  tempora molestiae assumenda nesciunt alias eius? Illo, autem!
                  <span className="text-lg leading-none italic font-bold text-gray-400 ml-1">
                    "
                  </span>
                </p>
              </div>
            </div>
            <div className="w-full mx-auto rounded-lg bg-white border border-gray-200 p-5 text-gray-800 font-light mb-6">
              <div className="w-full flex mb-4 justify-center items-center">
                <div className="overflow-hidden rounded-full w-12 h-12 bg-gray-50 border border-gray-200">
                  <Avatar src="https://i.pravatar.cc/100?img=7" />
                </div>
                <div className="pl-3">
                  <h6 className="font-bold text-sm uppercase text-gray-600">
                    Kris Stanton.
                  </h6>
                  <Rating value={5} />
                </div>
              </div>
              <div className="w-full">
                <p className="text-sm leading-tight">
                  <span className="text-lg leading-none italic font-bold text-gray-400 mr-1">
                    "
                  </span>
                  Lorem ipsum dolor sit amet consectetur adipisicing elit.
                  Voluptatem iusto, explicabo, cupiditate quas totam!
                  <span className="text-lg leading-none italic font-bold text-gray-400 ml-1">
                    "
                  </span>
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
