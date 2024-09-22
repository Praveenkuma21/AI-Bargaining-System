README for Price Negotiation Chatbot Project
Project Overview
This project features a Gemini-powered chatbot designed to assist customers in price negotiations. It leverages AI to analyze user requests and simulate negotiation strategies, providing real-time feedback on proposed prices.

Features
AI-Driven Negotiation: Uses Gemini AI to assess negotiation strategies and suggest optimal prices.
User-Friendly Interface: Simple and intuitive interaction for seamless customer engagement.
Real-Time Feedback: Provides immediate responses and adjustments during negotiations.
Technologies Used
Java: Primary programming language for backend development.
Spring Boot: Framework for building the RESTful API.
Gemini AI: For natural language processing and negotiation analysis.
JSON: For data interchange between the client and server.
Installation
Clone the repository:
bash
Copy code
git clone https://github.com/yourusername/your-repo.git
Navigate to the project directory:
bash
Copy code
cd your-repo
Build and run the application using Maven:
bash
Copy code
mvn spring-boot:run
Usage
Send a POST request to /api/negotiate with product details and negotiation parameters in JSON format.
Example Input
json
Copy code
{
  "name": "Laptop",
  "description": "High-performance laptop",
  "price": 1500,
  "quantity": 1,
  "targetPrice": 1200,
  "quantityRange": "1-5",
  "negotiationStrategy": "aggressive",
  "timeoutSeconds": 30
}
