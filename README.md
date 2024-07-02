# PetPal

## Introduction
PetPal is a web application that mediates connections between pet owners and caregivers in major Vietnamese cities. It enables users to contact each other, arrange meet-ups, and exchange pet care services. Safety is ensured through a thorough screening process, and the platform includes features for user registration, detailed pet profiles, and search/filter functionality.

### User Story
**Example:**
Emily is a pet owner with two cats, Meo and Miu. She is planning a weekend getaway and needs someone to feed and play with her cats. Emily logs into the PetPal platform and filters her search for caregivers who are willing to care for cats and are located within a 3km radius of her apartment.

The platform displays a map with nearby caregivers, and Emily can view their profiles and read reviews from other cat owners. She notices that one caregiver, Jessica, has a love for cats and has received excellent reviews for her attentive care. Emily initiates a chat with Jessica through the platform and explains her needs. They agree on a schedule, and Emily drops off her cats at Jessica’s place. Emily feels at ease knowing her cats are in good hands while she is away.

![PetPal Image](https://github.com/anhtuansggd/petPal/assets/35883067/067bad10-97e5-4bd9-b5d6-c6e0407f456d)

## Use Cases
1. **Registration, Login, and Logout:** Users can create accounts and securely log in and out.
2. **Location Display:** Shows current locations of users and caregivers to find nearby options.
3. **Filter Functionality:** Allows users to refine searches by pet type, distance, ranking, and price.
4. **Caregiver Information:** Displays basic info about nearby caregivers, including names, ages, and pet preferences.
5. **Map Display:** Provides a visual map of caregiver locations.
6. **Profile Update:** Users can update their profiles to keep information accurate.
7. **In-Platform Messaging:** Enables direct communication between pet owners and caregivers.
8. **Offer Management:** Allows pet owners to make offers to caregivers based on agreed terms.
9. **Caregiver Acceptance/Rejection:** Caregivers can accept or reject offers based on availability and preferences.
10. **Pet Preference Selection:** Caregivers can select pet types they care for and set their service prices.
11. **Canceling Check-In:** Users can cancel check-ins if plans change.
12. **Returning Pet:** Facilitates the return of pets to their owners.
13. **Rating and Reviewing:** Users can rate and review caregivers and pet owners.

## Key Use Cases
### Searching and Filtering
![Search and Filter](https://github.com/anhtuansggd/petPal/assets/35883067/d94fba65-a7f5-4cbb-b722-2e72e606403a)
![Search and Filter](https://github.com/anhtuansggd/petPal/assets/35883067/6f87def1-f72e-4ce2-9ddc-7d44686cb350)

### Check-In Pet
![Check-In Pet](https://github.com/anhtuansggd/petPal/assets/35883067/1baf8ca1-2688-4cd4-8465-af8b443b959b)
![Check-In Pet](https://github.com/anhtuansggd/petPal/assets/35883067/16458f0c-a728-45ce-a245-04707f983269)

### Returning Pet
![Returning Pet](https://github.com/anhtuansggd/petPal/assets/35883067/a0bce5f7-1e48-4443-9799-750af2b870a1)

## Database and Data Organization
![Database](https://github.com/anhtuansggd/petPal/assets/35883067/242b0b73-c97b-4574-9dad-3bbc55faff10)
![Database](https://github.com/anhtuansggd/petPal/assets/35883067/fce264d4-2be8-4915-bbf8-ebf561c27d25)

## Backend and Frontend
![Backend-Frontend](https://github.com/anhtuansggd/petPal/assets/35883067/e29621ad-6c57-4391-bb5a-e20d2d47dba3)
1. **petpalDB:**
    - **Description:** The database container.
    - **Role:** Stores all the application data.

2. **backend:**
    - **Description:** The Spring Java application container.
    - **Role:** Manages server-side logic and operations.

3. **frontend:**
    - **Description:** The Next.js application container.
    - **Role:** Handles client-side interactions and user interface.

4. **pgAdmin:**
    - **Description:** A web-based database management tool container.
    - **Role:** Facilitates database management and interaction through a web interface.

5. **Nginx:**
    - **Description:** A web server and reverse proxy container.
    - **Role:** Routes HTTP requests and ensures secure HTTPS connections.

6. **SSL-Service:**
    - **Description:** A service using `certbot` to obtain SSL certificates.
    - **Role:** Secures the application by enabling HTTPS encryption.
