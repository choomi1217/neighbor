package who.is.neighbor.enums;

import who.is.neighbor.geometry.application.manager.EupmyeondongManager;
import who.is.neighbor.geometry.application.manager.GeometryEntityManager;
import who.is.neighbor.geometry.application.manager.SidoManager;
import who.is.neighbor.geometry.application.manager.SigunguManager;

public enum GeometryTableFactory {
    SIDO {
        @Override
        public GeometryEntityManager getManager() {
            return new SidoManager();
        }
    },
    SIGUNGU {
        @Override
        public GeometryEntityManager getManager() {
            return new SigunguManager();
        }
    },
    EUPMYEONDONG {
        @Override
        public GeometryEntityManager getManager() {
            return new EupmyeondongManager();
        }
    };

    public abstract GeometryEntityManager getManager();
}

