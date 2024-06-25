FROM node:20-alpine3.19
LABEL authors="thong"

# Create a non-root user
RUN addgroup nodejs
RUN adduser -D nextjs

WORKDIR /frontend

# Copy package.json and package-lock.json
COPY package.json package-lock.json ./

COPY . .

# Install dependencies
RUN npm install

# Build the Next.js application
RUN npm run build
RUN chown nextjs:nodejs .next

# Set ownership and permissions
RUN chown -R nextjs:nodejs .next

#Set non-root user
USER nextjs
# Set the command to start your app
CMD ["npm", "run", "start"]

# Expose the port the app runs on
# EXPOSE 3000
#docker run -i --entrypoint /bin/sh -p 3000:3000 --network db-nework --name frContainer front-end
