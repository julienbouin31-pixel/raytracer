# Java RayTracer

***<u>Auteurs</u> : Julien Bouin et Bruno Hanna***

Ce projet est un moteur de rendu **Ray Tracer** développé en Java. Ce Ray Tracer en Java génère des images 3D réalistes en simulant le comportement physique de la lumière.

Le projet inclut un parseur de scènes personnalisé, la gestion de l'éclairage (Lambert & Phong), des ombres portées et des réflexions (miroirs).

***Le rendu des deux fichiers finaux ( final.scene et final_avec_bonus.scene ) se trouve dans le dossier "src/main/resources/output_images/final_image/"***

## Fonctionnalités

* **Primitives Géométriques :**
    * Sphères
    * Plans
    * Triangles
* **Éclairage & Matériaux :**
    * Lumière Ambiante
    * Lumière Ponctuelle (Point Light)
    * Lumière Directionnelle (Sun Light)
    * Modèle d'ombrage de **Phong** (Composantes Diffuse et Spéculaire)
* **Rendu Avancé :**
    * **Ombres portées** (Shadow rays)
    * **Réflexion récursive** (Objets miroirs avec `maxdepth`)
* **Système de Scène :**
    * Fichiers de configuration `.scene` personnalisés.
    * Export automatique des images en `.png`.
* **Qualité & Tests :**
    * Comparaison d'images pixel-par-pixel pour les tests de non-régression.
    * Gestion automatique des dossiers de sortie.

---

## 📂 Arborescence Complète du Projet

Voici la structure détaillée du code source et des ressources.

```text
raytracer/
├── pom.xml                             # Configuration Maven (dépendances, build)
├── README.md                           # Documentation du projet
│
├── src/
│   ├── main/
│   │   ├── java/fr/imt/
│   │   │   ├── Main.java               # 🚀 Point d'entrée : charge la scène et lance le Renderer
│   │   │   ├── ImageComparator.java    # 🛠️ Utilitaire pour comparer deux images (pixel par pixel)
│   │   │   │
│   │   │   └── raytracer/
│   │   │       ├── geometry/           # 📐 Formes et Mathématiques
│   │   │       │   ├── AbstractVec3.java   # Classe de base pour les vecteurs (x, y, z)
│   │   │       │   ├── Point.java          # Représente une position dans l'espace 3D
│   │   │       │   ├── Vector.java         # Représente une direction ou une normale
│   │   │       │   ├── Shape.java          # Classe abstraite pour tous les objets (méthode intersect)
│   │   │       │   ├── Sphere.java         # Implémentation de la Sphère
│   │   │       │   ├── Plane.java          # Implémentation du Plan infini
│   │   │       │   └── Triangle.java       # Implémentation du Triangle
│   │   │       │
│   │   │       ├── imaging/            # 🎨 Gestion de l'image
│   │   │       │   └── Color.java          # Gestion des couleurs RVB, HDR et clamping
│   │   │       │
│   │   │       ├── parsing/            # 📝 Lecture de fichiers
│   │   │       │   └── SceneFileParser.java # Lit les fichiers .scene et instancie les objets
│   │   │       │
│   │   │       └── raytracer/          # 🎬 Cœur du Moteur de Rendu
│   │   │           ├── Scene.java          # Conteneur (Caméra, Liste de Shapes, Liste de Lights)
│   │   │           ├── Renderer.java       # Boucle principale : itère sur les pixels (x,y)
│   │   │           ├── RayTracer.java      # Calcule la couleur d'un pixel donné (lancer de rayon)
│   │   │           ├── Ray.java            # Définit un rayon (Origine + Direction)
│   │   │           ├── Camera.java         # Gère la position de l'œil, le "lookAt" et le FOV
│   │   │           ├── Intersection.java   # Stocke les infos d'un impact (t, point, normale, objet)
│   │   │           ├── Orthonormal.java    # Base orthonormée (u, v, w) pour la caméra
│   │   │           ├── Light.java          # Interface pour les lumières
│   │   │           ├── PointLight.java     # Lumière positionnelle (ex: ampoule)
│   │   │           └── DirectionalLight.java # Lumière directionnelle (ex: soleil)
│   │   │
│   │   └── resources/
│   │       ├── output_images/          # 🖼️ Dossier de destination des images générées
                ├── final_image/        # Dossier qui contient le rendu des deux fichiers finaux ( final.scene et final_avec_bonus.scene )
│   │       ├── scenes/                 # 📄 Fichiers de scènes (.scene) et images de référence (.png)
│   │       │   ├── final.scene            
                ├── final_avec_bonus.scene
│   │       │   ├── jalonX/                 # Scènes de tests par étapes
│   │       │   └── 
│   │       └── topic/                  # 📚 Documentation PDF du sujet
│   │
│   └── test/java/fr/imt/raytracer/maths/
│       ├── ColorTest.java              # Tests unitaires pour la classe Color
│       ├── PointTest.java              # Tests unitaires pour la classe Point
│       ├── VectorTest.java             # Tests unitaires pour la classe Vector
│       └── RayTracerTest.java          # ✅ Tests d'intégration (compare le rendu aux images de référence)
│
└── target/                             # Dossier de compilation (généré par Maven)
```


## 🚀 Exécution de l'application

Vous pouvez lancer l'application de deux manières : via le fichier JAR pré-compilé ou via la ligne de commande (Maven).

### 1. Via le fichier JAR
Un exécutable `.jar` est disponible dans le dossier des artifacts.

```bash
java -jar ./out/artifacts/raytracer_jar/raytracer.jar src/main/resources/scenes/final.scene
```
### 2. Via Maven (Ligne de commande)

Si vous souhaitez recompiler le projet proprement avant de le lancer :

***Étape 1 : Compilation***

Bash
```
mvn clean compile
```

***Étape 2 : Exécution***

Bash
```
java -cp target/classes fr.imt.Main src/main/resources/scenes/final.scene
```
### 📂 Sortie

Une fois l'exécution terminée, l'image générée sera sauvegardée automatiquement dans le dossier : src/main/resources/output_images/

## 🧪 Lancer les Tests
Le projet contient des tests de non-régression qui comparent les images générées avec des images de référence officielles pour s'assurer que le code produit le bon résultat visuel mais aussi des tests unitaires pour la partie mathématiques.

***Pour lancer tous les tests :***

Bash
````
mvn test
````