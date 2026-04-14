# AI Test Basis Analyzer 🚀

A Senior SDET & AI Engineering solution designed to automate the conversion of **unstructured QA inputs** (requirements, logs, and API specs) into **structured, machine-readable datasets** and **automated test scaffolds**.

---

## 🛠️ How this helps QA
Manually extracting test conditions from lengthy PDFs, messy developer logs, and complex API specs is error-prone and time-consuming. This tool solves that by:
1.  **Automated Extraction:** Instantly identifies actors, actions, and validations from raw text.
2.  **Log Intelligence:** Automatically scans error logs to identify failure points and categorizes them as edge cases.
3.  **Traceability:** Bridges the gap between requirements and API specifications by merging them into a single "Source of Truth" (the Test Basis).
4.  **Instant Automation Scaffolding:** Converts extracted user flows directly into Playwright test scripts.

---

## 📊 How to Use the Report

This project generates multi-format outputs to serve different stakeholders in the QA lifecycle:

### 1. For Managers & Leads (`test-basis-report.html`)
Use the visual dashboard during **Sprint Grooming** or **Test Strategy** meetings. It provides:
- **Scope Visibility:** Instantly see how many entities and actions the system must support.
- **Risk Assessment:** Review the "Edge Cases" section (extracted from real logs) to ensure high-risk scenarios aren't overlooked.

### 2. For Automation Engineers (`generated-e2e.spec.js`)
The tool automatically scaffolds a **Playwright Test Suite**:
- **Zero-to-Test in Seconds:** Every identified user flow is converted into a structured `test()` block.
- **Action Mapping:** Each logical step in the flow is included as a comment/TODO, allowing engineers to focus on implementation rather than discovery.

### 3. For Data Pipelines (`test-basis.json`)
The structured JSON is designed for **Downstream Automation**:
- **BDD Generation:** Pass this JSON to a script to generate Gherkin `.feature` files.
- **Test Data Factory:** Use the `entities` list to seed your test databases.

---

## 🔍 How to Analyze the AI Test Basis

When you run the analyzer, it performs a **semantic merge** across your files. Here is how to analyze the results:

1. **Verify the Mapping:** Cross-check the **Validations** in the report against the **Requirements**.
2. **Identify Traceability Gaps:** Look for **Actions** found in the `api-spec.json` that are *missing* from requirements.
3. **Log-to-Test Transition:** The **Edge Cases** are extracted from `logs.txt`. Use these to create negative test cases for timeouts and exceptions.

---

## 📂 Project Structure
```text
AITestBasisAnalyzer/
├── src/
│   ├── main/
│   │   ├── java/com/testbasis/analyzer/
│   │   │   ├── models/            # Data models (TestBasisOutput)
│   │   │   ├── service/           # Core Logic (LLM, Playwright, HTML services)
│   │   │   └── AnalyzerApp.java   # Main entry point
│   │   └── resources/
│   │       └── input/             # 📥 Put your raw text/logs/json here
│   └── test/                      # 🧪 Automated JUnit tests
├── .env                           # 🔑 API Key storage (Git ignored)
├── test-basis.json                # 📤 Structured Machine-Readable output
├── test-basis-report.html         # 📊 Visual HTML Dashboard
├── generated-e2e.spec.js          # 🎭 Playwright Automation Scaffold
└── pom.xml                        # 📦 Maven dependencies (Jackson, OkHttp, JUnit)
```

---

## 🚀 Getting Started

### 1. Prerequisites
- **Java 17+** & **Maven**
- **OpenAI API Key**

### 2. Configuration
Rename `.env.example` to `.env` and add your key:
```env
OPENAI_API_KEY=sk-...
```

### 3. Running the Analyzer
```bash
mvn clean compile exec:java
```

### 4. Running Tests 🧪
```bash
mvn test
```

---

## 🔮 Future Scope & Enhancements

1.  **🔗 TCM Integration:** Add connectors for **JIRA, TestRail, or Zephyr** to push cases directly.
2.  **🛡️ PII Masking & Security:** Implement a local sanitization layer for logs.
3.  **📊 Traceability Matrix (RTM):** Generate dynamic mapping files between requirements and test cases.
4.  **📁 Multi-Source Ingestion:** Support for **PDF/DOCX** and **HAR** files.
5.  **🤖 Multi-LLM Support:** Support for Claude, Gemini, and Local LLMs (Ollama).

---
*Built with ❤️ by the AI SDET Team.*
