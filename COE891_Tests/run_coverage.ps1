mvn test "-Dtest=*Test" "-Dtest=!*LLMTest" jacoco:report
Copy-Item target\site\jacoco\jacoco.csv target\site\jacoco\jacoco_manual.csv -Force
mvn test "-Dtest=*LLMTest" jacoco:report
Copy-Item target\site\jacoco\jacoco.csv target\site\jacoco\jacoco_llm.csv -Force
Select-String -Pattern "BoyerMooreSearchMatcher$|HyperSearchRequest$|PatternSearchMatcher$" target\site\jacoco\jacoco_manual.csv,target\site\jacoco\jacoco_llm.csv,target\site\jacoco\jacoco_combined.csv | Out-String -Width 400
