/*
    This file is part of Bentokit Krispi Plugin Management System.

    OpenScore is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    OpenScore is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with OpenScore.  If not, see <http://www.gnu.org/licenses/>.
*/
package org.bentokit.krispi;

//import java.util.ArrayList;
//import java.util.Collection;

public class Tuple<T1, T2> implements java.io.Serializable {
        /**
	 * 
	 */
	private static final long serialVersionUID = -7810917439964482486L;
		public T1 first;
        public T2 second;

        public Tuple(T1 first, T2 second) {
                this.first = first;
                this.second = second;
        }
        
        public int hashCode() {
            return(first.hashCode()+second.hashCode());
        }

        public boolean equals(Tuple<T1,T2> t2) {
            if (!this.first.equals(t2.first)) return(false);
            if (!this.second.equals(t2.second)) return(false);
            return(true);
        }

/*
        public static Collection<?> firsts(Collection<Tuple<?,?>> tuples) {
            ArrayList<?> firsts = new ArrayList<?>();
            for (Tuple<?,?> t : tuples) {
                firsts.add(t.first);
            }
            return(firsts);
        }

        public static Collection<?> seconds(Collection<Tuple<?,?>> tuples) {
            ArrayList<?> seconds = new ArrayList<?>();
            for (Tuple<?,?> t : tuples) {
                seconds.add(t.second);
            }
            return(seconds);
        }
*/
}

