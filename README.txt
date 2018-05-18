{\rtf1\ansi\ansicpg1252\cocoartf1404\cocoasubrtf470
{\fonttbl\f0\froman\fcharset0 TimesNewRomanPSMT;\f1\fswiss\fcharset0 Helvetica;}
{\colortbl;\red255\green255\blue255;\red51\green51\blue51;\red255\green51\blue51;}
\paperw11900\paperh16840\margl1440\margr1440\vieww10800\viewh8400\viewkind0
\deftab709
\pard\pardeftab709\qc\partightenfactor0

\f0\fs36 \cf0 READ ME
\fs24 \
\pard\pardeftab709\partightenfactor0
\cf0 \
\pard\pardeftab709\partightenfactor0

\b \cf0 //Proc\'e9dure d\'92installation \'96 Avec Eclipse
\b0 \

\b \
\pard\pardeftab709\qj\partightenfactor0

\b0 \cf0 Apr\'e8s avoir suivit les instructions sur manuel Installation_Outils_Collector.pdf si jamais vous voulez avoir acc\'e8s au code tel que nous l\'92avons pratiqu\'e9. \
Il faut ensuite ouvrir Eclipse puis charger le projet qui est dans le dossier Collector. \
Vous devez imp\'e9rativement faire le chemin suivant pour charger le projet\'a0: File > Import > Existing project into workspace > Next > \'ab\'a0Select root directory\'a0\'bb.\
\pard\pardeftab709\partightenfactor0
\cf0 \
\
\pard\pardeftab709\partightenfactor0

\b \cf0 //Proc\'e9dure d\'92installation \'96 Sans Eclipse
\b0 \

\b \
\pard\pardeftab709\qj\partightenfactor0

\b0 \cf0 Gr\'e2ce \'e0 l\'92export Runnable JAR FileExport, vous n\'92avez plus besoin d\'92Eclipse pour tester notre code, il suffit d\'92ouvrir un terminal, vous rendre dans le dossier contenant Collector.jar gr\'e2ce \'e0 la commande :  
\f1\fs18 \cf2 cd [chemin d\'92acc\'e8s aufichier]
\f0\fs24 \cf0  et enfin taper cette ligne\'a0: 
\f1\fs18 \cf2 java -jar Collector.jar
\f0\fs24 \cf0 \
\pard\pardeftab709\partightenfactor0

\f1\fs18 \cf2 \
\pard\pardeftab709\partightenfactor0

\f0\fs24 \cf0 \
\pard\pardeftab709\partightenfactor0

\b \cf0 //Utilisation de l\'92application
\b0 \

\b  
\b0 \
\pard\pardeftab709\qj\partightenfactor0
\cf0 Si vous utilisez Collector, vous \'eates soit un Administrateur soit un \'e9boueur de l\'92entreprise. Une fois l\'92application lanc\'e9e, vous devez imp\'e9rativement vous authentifier avec votre login et mot de passe. Si ceci ne fonctionne pas, renseignez vous aupr\'e8s d\'92un Administrateur du syst\'e8me qui saura v\'e9rifier que vous faites bien partie de la base de donn\'e9es. \
\
Une fois l\'92authentification r\'e9alis\'e9e, vous ne pourrez pas r\'e9aliser les m\'eames fonctions selon votre statut. En effet, un administrateur pourra avoir acc\'e8s \'e0 diff\'e9rentes actions, tandis que l\'92\'e9boueur ne saura que visualiser la carte des collectes. \
\pard\pardeftab709\partightenfactor0
\cf0 \
	\ul \ulc0 Point de vue \'e9boueur\ulnone \
\pard\pardeftab709\partightenfactor0
\cf0 \ul \ulc0 \
\pard\pardeftab709\qj\partightenfactor0
\cf0 \ulnone En temps qu\'92\'e9boueur, vous pourrez donc visualiser la carte des collectes, pour cela, il faudra choisir entre visualiser votre carte personnelle ou la carte g\'e9n\'e9rale des collectes. Ensuite la carte choisie s\'92affichera d\'92elle-m\'eame.\
\pard\pardeftab709\partightenfactor0
\cf0 \
\
	\ul Point de vue administrateur\ulnone \
\pard\pardeftab709\partightenfactor0
\cf0 \ul \ulc0 \
\pard\pardeftab709\qj\partightenfactor0
\cf0 \ulnone En temps qu\'92administrateur, vous aurez le choix entre plusieurs actions\'a0: visualiser la carte (comme un \'e9boueur classique), modifier les diff\'e9rentes entr\'e9es de la base de donn\'e9es, avoir acc\'e8s aux diff\'e9rentes recherches de la base de donn\'e9e, calculer les statistiques et visualiser les statistiques. \
\
\pard\pardeftab709\qj\partightenfactor0
\cf3 Sp\'e9cification dans l\'92utilisation des diff\'e9rentes m\'e9thodes\cf0 \
\pard\pardeftab709\qj\partightenfactor0
\cf0 + La m\'e9thode recherche()\
Elle est utilis\'e9e dans les m\'e9thodes d\'92ajout, de modification et de suppression. Il faut bien faire attention quand on demande la condition de faire une condition SQL de type \'93 WHERE id_eboueur = 1\'94. \
\
+La m\'e9thode modifier()\
Attention de bien mettre r quand on ne veut pas changer un champ String et on met 0 quand c\'92est un champ d\'92integer. \
}