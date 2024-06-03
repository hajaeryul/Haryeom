import { ReactNode } from 'react';
import styled from 'styled-components';
import Header from '@/components//Header';
import ChatContainer from '@/containers/ChatContainer';
import { useRecoilValue } from 'recoil';
import userSessionAtom from '@/recoil/atoms/userSession';

interface HomeLayoutProps {
    children: ReactNode;
}

const HomeLayout = ({ children }: HomeLayoutProps) => {
    const userSession = useRecoilValue(userSessionAtom);

    return (
        <StyledHomeLayout>
            <Header />
            <ContainerWrapper>{children}</ContainerWrapper>
            {userSession && <ChatContainer />}
        </StyledHomeLayout>
    );
};

const StyledHomeLayout = styled.div`
    width: 100vw;
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: end;
`;

const ContainerWrapper = styled.main`
    width: 1150px;
    min-width: 1150px;
    height: calc(100% - 4em);
    display: flex;
`;

export default HomeLayout;
