/** @type {import('next').NextConfig} */
const nextConfig = {
    async rewrites() {
      return [
        {
          source: '/api/:path*',
          destination: 'http://backend:8081/api/:path*' 
        }
      ]
    }
  };

export default nextConfig;
