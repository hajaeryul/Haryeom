import styled from 'styled-components';
import { IHomework } from '@/apis/homework/homework';
import { IMyHomeworkDrawings } from '@/containers/HomeworkContainer/HomeworkContainer';

interface HomeworkStatusProps {
    homeworkData: IHomework;
    myHomeworkDrawings: IMyHomeworkDrawings;
}

const HomeworkStatus = ({ homeworkData, myHomeworkDrawings }: HomeworkStatusProps) => {
    const { startPage, endPage, drawings } = homeworkData;

    const renderPageButtons = () => {
        const pageRange = Array.from(
            { length: endPage - startPage + 1 },
            (_, index) => startPage + index
        );

        const getStatus = (page: number) => {
            if (drawings.some((drawing) => drawing.page === page)) return 'done';
            if (myHomeworkDrawings[page]) return 'progress';
        };

        return pageRange.map((page) => (
            <PageButton key={page} className={getStatus(page - startPage + 1)}>
                {page}
            </PageButton>
        ));
    };

    const calculateDoneCount = () => {
        return drawings.length;
    };

    const calculatePageLength = () => {
        return endPage - startPage + 1;
    };

    return (
        <StyledHomeworkStatus>
            <Title>
                숙제 현황 ({calculateDoneCount()}/{calculatePageLength()})
            </Title>
            <PageButttons>{renderPageButtons()}</PageButttons>
        </StyledHomeworkStatus>
    );
};

const StyledHomeworkStatus = styled.div`
    position: absolute;
    width: 150px;
    top: 4.6em;
    right: 3.5em;
    padding: 1em;
    border-radius: 1em;
    background-color: white;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
    border: 2px solid ${({ theme }) => theme.PRIMARY};
    font-size: 14px;

    @media (max-width: 1300px) {
        & {
            display: none;
        }
    }
`;

const Title = styled.div`
    font-weight: 500;
    padding: 0.3em 0.3em 0.8em 0.3em;
    border-bottom: 1px solid ${({ theme }) => theme.BORDER_LIGHT};
`;

const PageButttons = styled.div`
    width: 100%;
    padding-top: 0.8em;
    display: grid;
    grid-template-columns: repeat(3, 30px);
    gap: 10px;
    justify-content: center;
    align-content: center;
`;

const PageButton = styled.button`
    width: 30px;
    height: 30px;
    border: 1px solid ${({ theme }) => theme.BORDER_LIGHT};
    background-color: white;
    border-radius: 100%;

    &.progress {
        background-color: ${({ theme }) => theme.PRIMARY_LIGHT};
        border: none;
        color: white;
    }

    &.done {
        background-color: ${({ theme }) => theme.PRIMARY};
        border: none;
        color: white;
    }
`;

export default HomeworkStatus;
