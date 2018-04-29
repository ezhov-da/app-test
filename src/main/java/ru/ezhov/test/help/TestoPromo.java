package ru.ezhov.test.help;

        public class TestoPromo {
            static int humanScore = 0;
            static int orcScore = 0;

            public static void main(String[] args) {
                Human gladiatorH1 = new Human("Гавриил", 10, 10, 10);
                Orc gladiatorO1 = new Orc("Ибрагим", 12, 7, 11);
                fight(gladiatorO1, gladiatorH1);
                System.out.println("Текущий счет: люди " + humanScore + " Орки: " + orcScore);
            }

            public static void fight(Orc orcGlad, Human humanGlad) {//не могу понять что писать в этих скобках.
                /*Здесь я выясняю победит орк, человек, или будет ничья*/
                if (orcGlad.str + orcGlad.agi + orcGlad.vit > humanGlad.str + humanGlad.agi + humanGlad.vit)
                    orcScore++;
                if (orcGlad.str + orcGlad.agi + orcGlad.vit < humanGlad.str + humanGlad.agi + humanGlad.vit)
                    humanScore++;
            }
        }

        class Human {
            String name;
            int str;
            int agi;
            int vit;

            public Human() {
            }

            public Human(String name, int str, int agi, int vit) {
                this.name = name;
                this.str = str;
                this.agi = agi;
                this.vit = vit;

            }
        }

        class Orc {
            String name;
            int str;
            int agi;
            int vit;

            public Orc() {
            }

            public Orc(String name, int str, int agi, int vit) {
                this.name = name;
                this.str = str;
                this.agi = agi;
                this.vit = vit;

            }
        }