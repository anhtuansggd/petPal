FROM node:20-alpine3.19
LABEL authors="thong"

# Copy package.json and package-lock.json
COPY package.json package-lock.json ./

# Install dependencies
RUN npm install

# Copy the rest of your app's source code from your host to your image filesystem.
COPY . .

# Build the Next.js application
RUN npm run build

# Set the command to start your app
CMD ["npm", "start"]

# Expose the port the app runs on
EXPOSE 3000
#docker run -i --entrypoint /bin/sh -p 3000:3000 --network db-nework --name frContainer front-end
