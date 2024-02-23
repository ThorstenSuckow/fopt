# Leser-Schreiber-Problem

Das Leser-Schreiber-Problem faßt die Problematik zusammen, die entsteht, wenn Daten aktualisiert werden, und gleichzeitig die aktualisierten Daten gelesen werden sollen.

Während der lesende und schreibende Zugriff i.d.R. auf einfache Weise mit Hilfe der in Java vorhandenen Schlüsselkonzepte und Klassenbibliotheken synchronisiert ermöglicht werden kann, besteht ein weiteres Problem darin, eine Lösung dafür zu finden, welchen Threads bei dem Zugriff auf die Daten Vorrang gewährleistet werden soll.

Eine mögliche Lösung ist, den Schreibern Vorrang zu gewähren  - dann sind die Daten immer aktuell - allerdings kommen die Leser dann weniger oft zum Zug. 
Diese Strategie hat möglicherweise Auswirkungen auf Datenbanken, bei denen lesenden Anfragen länger auf Antworten warten müssen.

Eine alternative Lösungsmöglichkeit ist die Bevorzugung von Lesern, deren Anfragen möglichst rasch beantwortet werden. Da dann die Schreiber benachteiligt werden, erhalten die anfragenden Systeme aber nicht immer die aktuellsten Daten. 
